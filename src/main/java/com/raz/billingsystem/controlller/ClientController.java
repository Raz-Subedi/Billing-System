package com.raz.billingsystem.controlller;

import com.raz.billingsystem.model.Client;
import com.raz.billingsystem.service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/addClient")
    public String getClient(HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        return "ClientForm";
    }

    @PostMapping("/client")
    public String postClient(@ModelAttribute Client client,Model model,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        clientService.addClient(client);
        model.addAttribute("success","Added Client Sucessfully");
        return "ClientForm";
    }

    @GetMapping("/listClient")
    public String getListOfClient(Model model,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        model.addAttribute("cList",clientService.getAllClients());

        return "ClientListForm";
    }

    @GetMapping("/c_edit")
    public String editClient(@RequestParam int id,Model model,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        model.addAttribute("cModel",clientService.getClientById(id));

        return "ClientEditForm";
    }

    @PostMapping("/client/update")
    public String updateClient(@ModelAttribute Client client,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        clientService.updateClient(client);

        return "redirect:/listClient";
    }

    @GetMapping("/client/delete")
    public String deleteClient(@RequestParam int id,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        clientService.deleteClientById(id);

        return "redirect:/listClient";
    }

    public static boolean checkSession(HttpSession httpSession){
        if(httpSession.getAttribute("validUser") == null){
            httpSession.invalidate();
            return false;
        }
        return true;
    }
}
