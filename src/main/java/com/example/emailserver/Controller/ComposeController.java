package com.example.emailserver.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.emailserver.Service.Folders.Mail.Priority;
import com.example.emailserver.Service.Services.ComposeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;



@CrossOrigin
@RestController
public class ComposeController {
    private ComposeService service = new ComposeService();

    @PostMapping("/create/{userId}")
    public void createMail(@PathVariable String userId, @RequestBody String id) {
        service.createMail(userId, id);
    }

    @PutMapping("/body/text/{userId}/{id}")
    public String changeBody(@PathVariable String userId, @RequestBody(required = false) String text, @PathVariable String id) {
        service.changeText(userId, id, text);
        return text;
    }

    @PostMapping("/header/addto/{userId}/{id}")
    public String addTO(@PathVariable String userId, @RequestBody String email, @PathVariable String id) {
        service.addTo(userId, id, email);        
        return email;
    }
    
    @DeleteMapping("/header/removeto/{userId}/{id}/{email}")
    public String removeTO(@PathVariable String userId, @PathVariable String email, @PathVariable String id) {
        service.removeTO(userId, id, email);        
        return email;
    }

    @PutMapping("header/from/{userId}/{id}")
    public String changeFrom(@PathVariable String userId, @RequestBody String from, @PathVariable String id) {
        service.changeFrom(userId, id, from);        
        return from;
    }

    @PutMapping("header/subject/{userId}/{id}")
    public String changeSubject(@PathVariable String userId, @RequestBody(required = false) String subject, @PathVariable String id) {
        service.changeSubject(userId, id, subject);        
        return subject;
    }

    @PutMapping("header/priority/{userId}/{id}")
    public String changePriority(@PathVariable String userId, @RequestBody String priority, @PathVariable String id) {
        service.changePriority(userId, id, Priority.valueOf(priority));        
        return priority;
    }

    @PostMapping("/body/attachment/{userId}/{id}")
    public void addAttachment(@PathVariable String userId, @RequestParam("file") MultipartFile file, @PathVariable String id) {
        service.addAttachment(userId, id, file);
    }

    @DeleteMapping("/body/attachment/{userId}/{id}/{fileName}")
    public void deleteAttachment(@PathVariable String userId, @PathVariable String id, @PathVariable String fileName) {
        service.deleteAttachment(userId, id, fileName);
    }
    
    @GetMapping("/body/attachment/{userId}/{id}/{fileName}")
    public ResponseEntity<InputStreamResource> getAttachment(@PathVariable String userId, @PathVariable String id, @PathVariable String fileName) throws FileNotFoundException {
        File file = service.getAttachmentFile(userId, id, fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
        
    }
}
