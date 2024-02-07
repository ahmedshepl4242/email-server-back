package com.example.emailserver.Controller;


//import com.example.emailserver.Controller.Requests.ContactRequest;

import com.example.emailserver.Service.Contacts.Contact;
import com.example.emailserver.Service.Services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/addContact")
    public String addContact(@RequestParam String id, @RequestParam String name, @RequestParam String email) {

//        System.out.println(contactRequest.getId()+"--------"+contactRequest.getName()+" "+contactRequest.getEmails());
//        contactService.addContact(contactRequest.getId(), contactRequest.getName(),contactRequest.getEmails());
        System.out.println(id + "  " + name + " " + email);
        contactService.addContact(id, name, email);

        return "contact created";
    }

    @PostMapping("/deleteContact")
    public String deleteContact(@RequestParam String id, @RequestParam String email) {
        System.out.println(id + " " + email);
        contactService.deleteContact(id, email);
        return "contact deleted";
    }

    @PostMapping("/search")
    public Contact search(@RequestParam String id, @RequestParam String name) {
        Contact contact = contactService.searchName(id, name);
        return contact;

    }

    @PostMapping("/sortContacts")
    public ArrayList<Contact> sortContacts(@RequestParam String id) {

        return contactService.sort(id);

//        ArrayList<Contact> arr = new ArrayList<>();
//
//        arr.add(new Contact("sd", "asas"));
//        arr.add(new Contact("sdassaas", "fddfs"));
//        arr.add(new Contact("sasasa", "sa"));
//        arr.add(new Contact("assa", "asas"));

//        return arr;

    }

//    @GetMapping("/getContacts")
//    public String getContacts() {
//        List<Contact> contacts = contactService.getContacts();
//        return "contacts obtained";
//
//    }

    @GetMapping("")
    public String get() {
        return "dsds";
    }


    @PostMapping("/editContact")
    public String editContact(@RequestParam String id, @RequestParam String name, @RequestParam String email) {
//        System.out.println(contactRequest.getId()+"--------"+contactRequest.getName()+" "+contactRequest.getEmails());
//        contactService.editContact(contactRequest.getId(), contactRequest.getName(),contactRequest.getEmails());

        System.out.println(id + "  " + name + " " + email);
        contactService.addContact(id, name, email);

        return "contact edited";
    }
}