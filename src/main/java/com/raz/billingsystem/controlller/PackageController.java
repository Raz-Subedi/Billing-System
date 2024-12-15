package com.raz.billingsystem.controlller;

import com.raz.billingsystem.model.Package;
import com.raz.billingsystem.service.PackageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PackageController {

    @Autowired
    private PackageService packService;

    @GetMapping("/addPackage")
    public String getPackage(HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }

        return "PackageForm";
    }

    @PostMapping("/package")
    public String postPackage(@ModelAttribute Package pack,Model model,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        packService.addPackage(pack);
        model.addAttribute("success","Sucessfully Added!");

        return "PackageForm";
    }

    @GetMapping("/listPackage")
    public String getPackageList(Model model,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }

        model.addAttribute("pList",packService.getAllPackage());
        return "PackageListForm";
    }

    @GetMapping("/edit")
    public String editPackage(@RequestParam int id,Model model,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        model.addAttribute("pModel",packService.getPackageById(id));

        return "PackageEditForm";
    }

    @PostMapping("/pack/update")
    public String updatePackage(@ModelAttribute Package pack,Model model,HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        packService.updatePackage(pack);
        model.addAttribute("success","Sucessfully Updated");

        return "redirect:/listPackage";
    }

    @GetMapping("/pack/delete")
    public String deletePackage(@RequestParam int id, HttpSession httpSession){
        if(!checkSession(httpSession)){
            return "redirect:/login";
        }
        packService.deletePackage(id);

        return "redirect:/listPackage";
    }

    public static boolean checkSession(HttpSession httpSession){
        if(httpSession.getAttribute("validUser") == null){
            httpSession.invalidate();
            return false;
        }
        return true;
    }
}
