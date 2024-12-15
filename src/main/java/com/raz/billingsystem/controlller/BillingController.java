package com.raz.billingsystem.controlller;

import com.raz.billingsystem.model.Billing;
import com.raz.billingsystem.model.Client;
import com.raz.billingsystem.model.Package;
import com.raz.billingsystem.service.BillingService;
import com.raz.billingsystem.service.ClientService;
import com.raz.billingsystem.service.PackageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class BillingController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private BillingService billService;

    @GetMapping("/billing")
    public String getClient(HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }

        return "SearchClientForm";
    }

    @PostMapping("/searchClient")
    public String searchClient(@ModelAttribute Client client, Model model,HttpSession httpSession) {
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        List<Client> clients = clientService.searchClient(client.getFname(), client.getLname());

        if (clients.isEmpty()) {
            model.addAttribute("failed", "Client Not Found!");
            return "SearchClientForm";
        } else if (clients.size() == 1) {
            model.addAttribute("message","Client Found!");
            model.addAttribute("cModel", clients.get(0));
            model.addAttribute("pList", packageService.getAllPackage());
            return "ClientInformation";
        } else {
            model.addAttribute("message","MULTIPLE CLIENT EXIST!");
            model.addAttribute("clients", clients);
            return "ClientSelection";
        }
    }

    @PostMapping("/selectClient")
    public String selectClient(@RequestParam("phone") String phone, Model model,HttpSession httpSession) {
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        Client selectedClient = clientService.getClientByPhone(phone);
        model.addAttribute("cModel", selectedClient);
        model.addAttribute("pList", packageService.getAllPackage());
        return "ClientInformation";
    }

    @PostMapping("/next")
    public String next(@ModelAttribute Client client, @RequestParam("pack_name") String packageName, Model model,HttpSession httpSession) {
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        // Fetch package details based on the selected package name
        Package p = packageService.getPackageByName(packageName);

        // Create a new Billing object and populate it with client and package details
        Billing billing = new Billing();
        billing.setFname(client.getFname());
        billing.setLname(client.getLname());
        billing.setPhone(client.getPhone());
        billing.setEmail(client.getEmail());
        billing.setCity(client.getAddress().getCity());
        billing.setCountry(client.getAddress().getCountry());
        billing.setPack_name(p.getPname());
        billing.setPack_price(p.getPprice());

        // Add the Billing object to the model
        model.addAttribute("billing", billing);

        billService.addBill(billing);

        return "BillingPage";
    }


    @PostMapping("/generate")
    public String generateBill(@RequestParam("status") String s,@RequestParam("b_id") int id, Model model,HttpSession httpSession) {
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }

//        billService.addStatus(billing);

        Billing bill = billService.getBillById(id);

            if (bill != null) {
                model.addAttribute("b", bill.getB_id());
                model.addAttribute("f", bill.getFname());
                model.addAttribute("l", bill.getLname());
                model.addAttribute("p", bill.getPhone());
                model.addAttribute("e", bill.getEmail());
                model.addAttribute("c", bill.getCity());
                model.addAttribute("co", bill.getCountry());
                model.addAttribute("pn", bill.getPack_name());
                model.addAttribute("pp", bill.getPack_price());

                bill.setStatus(s);
                model.addAttribute("s",bill.getStatus());

                billService.addBill(bill);

                SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String dd = dFormat.format(date);

                bill.setDate(date);
                model.addAttribute("d",dd);

                billService.addBill(bill);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String tt = dtf.format(now);
                model.addAttribute("t",tt);

                long subtotal = (bill.getPack_price());
                long tax = (13*subtotal)/100;
                long total = subtotal+tax;
                bill.setTotal(total);
                billService.addBill(bill);
                model.addAttribute("st",subtotal);
                model.addAttribute("ta",tax);
                model.addAttribute("tol",total);

                return "GeneratePage";
            }
            else {
                model.addAttribute("error", "Billing record not found.");
                return "ErrorPage";
            }
        }

        @GetMapping("/historysearch")
        public String gethistory(Model model,HttpSession httpSession){
            if(!checkSession(httpSession)){
                return "redirect:/login";
            }
//            model.addAttribute("hList",billService.getAllHistory());

            return "SearchHistory";
        }

    @GetMapping("/paid")
    public String getPaidClients(Model model,HttpSession httpSession) {
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        model.addAttribute("clients", billService.getClientsByStatus("Paid"));
        model.addAttribute("totalAmount", billService.getTotalAmountByStatus("Paid"));
        return "PaidClients";
    }

    @GetMapping("/unpaid")
    public String getUnpaidClients(Model model,HttpSession httpSession) {
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        model.addAttribute("clients", billService.getClientsByStatus("Unpaid"));
        model.addAttribute("totalAmount", billService.getTotalAmountByStatus("Unpaid"));
        return "UnpaidClients";
    }

    @PostMapping("/markAsPaid")
    public String markAsPaid(@RequestParam("clientId") int clientId, RedirectAttributes redirectAttributes,HttpSession httpSession) {
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        boolean updated = billService.markAsPaid(clientId);
        if (updated) {
            redirectAttributes.addFlashAttribute("successMessage", "Client marked as paid successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to mark client as paid.");
        }
        return "redirect:/unpaid";
    }


    @GetMapping("/history")
    public String searchHistory(@RequestParam("fromDate") String fromDateStr,
                                @RequestParam("toDate") String toDateStr,
                                Model model) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = dateFormat.parse(fromDateStr);
        Date toDate = dateFormat.parse(toDateStr);

        List<Billing> billingHistory = billService.getBillingHistoryByDateRange(fromDate, toDate);

        model.addAttribute("billingHistory", billingHistory);
        return "HistoryPage"; // replace with your actual view name
    }

    public static boolean checkSession(HttpSession httpSession){
        if(httpSession.getAttribute("validUser") == null){
            httpSession.invalidate();
            return false;
        }
        return true;
    }
}




