package com.example.emailserver.Service.DAO;

import com.example.emailserver.Service.Contacts.Contact;
import com.example.emailserver.Service.Folders.*;
import com.example.emailserver.Service.Folders.Mail.Body;
import com.example.emailserver.Service.Folders.Mail.Header;
import com.example.emailserver.Service.Folders.Mail.Mail;
import com.example.emailserver.Service.Folders.Mail.Priority;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class managerDB {

    static String path = "./dataBase.json";
    static boolean flag = false;
    public static LinkedList<String> cached_id = new LinkedList<String>();
    public static HashMap<String, Account> cache = new HashMap<>();

    public static boolean excute() {
        try {
            File jFile = new File(path);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jFile);

//            getAllIdea();

            if (rootNode != null && rootNode.isArray()) {


                ArrayNode arrayNode = (ArrayNode) rootNode;

                for (JsonNode accountNode : arrayNode) {


                    Account account = extractAccountFromJson(accountNode, objectMapper);

                    System.out.println(account);

                    managerDB.cache.put(account.getId(), account);
                }

                return true;


            } else {
                System.out.println("The JSON file does not contain an array.");
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public static Account extractAccountFromJson(JsonNode accountNode, ObjectMapper objectMapper) {
        try {
            Account account = new Account();

            // Extract simple properties
            account.setId(accountNode.get("id").asText());
            account.setName(accountNode.get("name").asText());
            account.setPassword(accountNode.get("password").asText());

            // Extract inbox
            JsonNode inboxNode = accountNode.get("inbox");
            IMailFolders inbox = (IMailFolders) extractfolder(inboxNode, objectMapper);
            account.setInbox((Inbox) inbox);

            // Extract trash
            JsonNode trashNode = accountNode.get("trash");
            IMailFolders trash = (IMailFolders) extractfolder(trashNode, objectMapper);
            account.setTrash((Trash) trash);
            account.setTrash(new Trash("trash"));
            // Extract draft
            JsonNode draftNode = accountNode.get("draft");
            IMailFolders draft = (IMailFolders) extractfolder(draftNode, objectMapper);
            account.setDraft((Draft) draft);

//             Extract sent folders
            JsonNode sentFoldersNode = accountNode.get("sentfolders");
            IMailFolders sentFolders = (IMailFolders) extractfolder(sentFoldersNode, objectMapper);
            account.setSentfolders((SentFolders) sentFolders);

            // Extract user folders
            JsonNode userFoldersNode = accountNode.get("userFolders");
            extractUserFoldersFromJson(userFoldersNode, account, objectMapper);

            // Extract contacts
            JsonNode contactsNode = accountNode.get("contacts");
            extractContactsFromJson(contactsNode, account, objectMapper);

            return account;
        } catch (Exception e) {
            System.out.println("Error extracting Account from JSON: " + e.getMessage());
            return null;
        }
    }

    private static IMailFolders extractfolder(JsonNode ImailFolderNode, ObjectMapper objectMapper) {


        System.out.println(ImailFolderNode.get("name").asText());
        try {

            MailFolders folder = (MailFolders) managerDB.choose(ImailFolderNode.get("name").asText());


            JsonNode emailsNode = ImailFolderNode.get("emails");
            extractEmailsFromJson(emailsNode, folder, objectMapper);

            return folder;

            // Extract mail properties


        } catch (Exception e) {
            System.out.println("Error extracting Folder from JSON: " + e.getMessage());
            return null;
        }
    }

    private static Mail extractMailFromJson(JsonNode mailNode, ObjectMapper objectMapper) {
        try {
            Mail mail = new Mail();

            // Extract mail properties
            System.out.println(mailNode.get("id").asText());
            mail.setId(mailNode.get("id").asText());

            // Extract body
            JsonNode bodyNode = mailNode.get("body");
            Body body = extractBodyFromJson(bodyNode, objectMapper);
            mail.setBody(body);

            // Extract header
            JsonNode headerNode = mailNode.get("header");
            Header header = extractHeaderFromJson(headerNode, objectMapper);
            mail.setHeader(header);

            return mail;
        } catch (Exception e) {
            System.out.println("Error extracting email from JSON: " + e.getMessage());
            return null;
        }
    }

    private static Body extractBodyFromJson(JsonNode bodyNode, ObjectMapper objectMapper) {
        try {
            Body body = new Body();

            // Extract body properties
            body.setText(bodyNode.get("text").asText());


            // Extract attachments if available
            if (bodyNode.has("attachment") && bodyNode.get("attachment").isArray()) {


            }

            return body;
        } catch (Exception e) {
            System.out.println("Error extracting Body from JSON: " + e.getMessage());
            return null;
        }
    }

    private static Header extractHeaderFromJson(JsonNode headerNode, ObjectMapper objectMapper) {
        try {
            Header header = new Header();
            String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
            // Extract header properties
            header.setFrom(headerNode.get("from").asText());
            header.setSubject(headerNode.get("subject").asText());
            header.setPriority(Priority.valueOf(headerNode.get("priority").asText()));
            header.setDate(dateFormat.parse(headerNode.get("date").asText()));

            // Extract recipients if available
            JsonNode toNode = headerNode.get("to");
//            if (toNode.isArray()) {
//                // Implement logic to extract recipients if needed
//            }

            return header;
        } catch (Exception e) {
            System.out.println("Error extracting Header from JSON: " + e.getMessage());
            return null;
        }
    }

    private static void extractUserFoldersFromJson(JsonNode userFoldersNode, Account account, ObjectMapper objectMapper) {
        try {
            for (JsonNode userFolderNode : userFoldersNode) {
                UserFolder userFolder = new UserFolder((userFolderNode.get("name").asText()));

                // Extract user folder properties
                userFolder.setId(userFolderNode.get("id").asText());
//                userFolder.setName(userFolderNode.get("name").asText());

                // Extract emails in the user folder
                JsonNode emailsNode = userFolderNode.get("emails");
                extractEmailsFromJson(emailsNode, userFolder, objectMapper);

                account.getUserFolders().put(userFolder.getId(), userFolder);
            }
        } catch (Exception e) {
            System.out.println("Error extracting UserFolders from JSON: " + e.getMessage());
        }
    }

    private static void extractEmailsFromJson(JsonNode emailsNode, MailFolders userFolder, ObjectMapper objectMapper) {
        try {
            for (JsonNode emailNode : emailsNode) {
                Mail email = extractMailFromJson(emailNode, objectMapper);
                userFolder.getEmails().put(email.getId(), email);
            }
        } catch (Exception e) {
            System.out.println("Error extracting Emails from JSON: " + e.getMessage());
        }
    }

    private static void extractContactsFromJson(JsonNode contactsNode, Account account, ObjectMapper objectMapper) {
        try {
            for (JsonNode contactNode : contactsNode) {
                Contact contact = new Contact();

                // Extract contact properties
                contact.setName(contactNode.get("name").asText());

                // Extract emails in the contact
                String email = contactNode.get("emails").asText();
                contact.setEmails(email);

                account.getContactsRegistery().getHash().put(contact.getEmails(), contact);
            }
        } catch (Exception e) {
            System.out.println("Error extracting Contacts from JSON: " + e.getMessage());
        }
    }
//
//    public static void writeToDb(Account account) {
//        try {
//            File file = new File(path);
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // Serialize the account object to a JSON string
//            String accountJson = objectMapper.writeValueAsString(account);
//
//            // Check if the file exists
//            if (!file.exists()) {
//                // If not, create a new file and write the account JSON string to it
//                FileWriter fileWriter = new FileWriter(file);
//                fileWriter.write("[\n");
//                fileWriter.write(accountJson);
//                fileWriter.write("\n]");
//                fileWriter.flush();
//                fileWriter.close();
//            } else {
//                // If the file exists, append the account JSON string to it
//                FileWriter fileWriter = new FileWriter(file, true);
//                fileWriter.write("\n");
//                fileWriter.write(accountJson);
//                fileWriter.flush();
//                fileWriter.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    public static void writeToDb(Account account) {

        System.out.println(account.getId());


        try {
            File jFile = new File(path);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jFile);
            ArrayNode arrayNode;
//            String json =objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(account);
            if (rootNode == null || !rootNode.isArray()) {
                // If the root node doesn't exist or is not an array, create a new array
                arrayNode = objectMapper.createArrayNode();
            } else {
                arrayNode = (ArrayNode) rootNode;
            }

//                Gson gson = new Gson();
//                String json=gson.toString();

//                // Create a new ObjectNode with the desired values
            ObjectNode newNode = objectMapper.createObjectNode();
            newNode.put("id", account.getId());
            newNode.put("name", account.getName());
            newNode.put("password", account.getPassword());
//
            ObjectNode objInbox = objectMapper.createObjectNode();
            objInbox.put("name", account.getInbox().getName());
            ObjectNode emailsNode = objectMapper.createObjectNode();
            for (Map.Entry<String, Mail> entry : account.getInbox().getEmails().entrySet()) {
                emailsNode.put(entry.getKey(), converttoemail(entry.getValue(), objectMapper));
            }
            objInbox.put("emails", emailsNode);
            newNode.put("inbox", objInbox);


            ObjectNode emailsNode3 = objectMapper.createObjectNode();
            ObjectNode trashnode = objectMapper.createObjectNode();
            trashnode.put("name", account.getTrash().getName());

            for (Map.Entry<String, Mail> entry : account.getTrash().getEmails().entrySet()) {
                emailsNode3.put(entry.getKey(), converttoemail(entry.getValue(), objectMapper));
            }
            trashnode.put("emails", emailsNode3);
            newNode.put("trash", trashnode);


            ObjectNode draft = objectMapper.createObjectNode();
            draft.put("name", account.getDraft().getName());
            ObjectNode emailsNode1 = objectMapper.createObjectNode();
            for (Map.Entry<String, Mail> entry : account.getDraft().getEmails().entrySet()) {
                emailsNode1.put(entry.getKey(), converttoemail(entry.getValue(), objectMapper));
            }
            draft.put("emails", emailsNode1);
            newNode.put("draft", draft);


            ObjectNode sentfolders = objectMapper.createObjectNode();
            sentfolders.put("name", account.getSentfolders().getName());
            ObjectNode emailsNode2 = objectMapper.createObjectNode();
            for (Map.Entry<String, Mail> entry : account.getSentfolders().getEmails().entrySet()) {
                emailsNode2.put(entry.getKey(), converttoemail(entry.getValue(), objectMapper));
            }
            sentfolders.put("emails", emailsNode2);
            newNode.put("sentfolders", sentfolders);


            ObjectNode userfolders = objectMapper.createObjectNode();
            for (Map.Entry<String, UserFolder> entry : account.getUserFolders().entrySet()) {
                ObjectNode userfolder = objectMapper.createObjectNode();
                userfolder.put("id", entry.getValue().getId());
                userfolder.put("name", entry.getValue().getName());
                ObjectNode emailsNode7 = objectMapper.createObjectNode();
                for (Map.Entry<String, Mail> ent : entry.getValue().getEmails().entrySet()) {
                    emailsNode7.put(ent.getKey(), converttoemail(ent.getValue(), objectMapper));
                    userfolder.put(ent.getKey(), emailsNode7);
                }
                userfolders.put(entry.getKey(), userfolder);
            }
            newNode.put("userFolders", userfolders);


            ObjectNode contacts = objectMapper.createObjectNode();
            for (Map.Entry<String, Contact> entry : account.getContactsRegistery().getHash().entrySet()) {
                ObjectNode con = objectMapper.createObjectNode();

                con.put("name", entry.getValue().getName());
                con.put("emails", entry.getValue().getEmails());

                contacts.put(entry.getKey(), con);
            }
            newNode.put("contacts", contacts);


            arrayNode.add(newNode);


            System.out.println("Object appended.");
            // Write the modified JSON back to the file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jFile, arrayNode);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ObjectNode converttoemail(Mail mail, ObjectMapper objectMapper) {
        try {
            // Create an ObjectNode to represent the Mail object
            com.fasterxml.jackson.databind.node.ObjectNode mailNode = objectMapper.createObjectNode();

            // Add attributes to the ObjectNode
            mailNode.put("id", mail.getId());

            // Create ObjectNode for "body"
            com.fasterxml.jackson.databind.node.ObjectNode bodyNode = objectMapper.createObjectNode();
            bodyNode.put("text", mail.getBody().getText());
            bodyNode.putArray("attachment"); // Assuming attachment is a property in the Body class
            // You may add more attributes to the "body" as needed

            // Create ObjectNode for "header"
            com.fasterxml.jackson.databind.node.ObjectNode headerNode = objectMapper.createObjectNode();
            headerNode.putArray("to"); // an empty array for "to"
            headerNode.put("from", mail.getHeader().getFrom());
            headerNode.put("subject", mail.getHeader().getSubject());
            headerNode.put("priority", mail.getHeader().getPriority().toString());
            headerNode.put("date", mail.getHeader().getDate().toString());
            // You may add more attributes to the "header" as needed

            // Set "body" and "header" within the mailNode
            mailNode.set("body", bodyNode);
            mailNode.set("header", headerNode);
            return mailNode;

            // Convert the ObjectNode to a JSON string

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your requirements
            return null;
        }

    }

    public static JsonNode hashMaptoJson(HashMap<String, UserFolder> hs, ObjectMapper objectMapper) {
        ObjectNode mailFolderNode = objectMapper.createObjectNode();


        // Convert the HashMap<String, Mail> to JsonNode
        JsonNode emailsNode = objectMapper.valueToTree(hs);
        mailFolderNode.set("userFolders", emailsNode);

        return mailFolderNode;
    }

    public static void deleteDB(String idToDelete) {

        try {
            File jFile = new File(path);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jFile);

            if (rootNode.isArray()) {
                ArrayNode arrayNode = (ArrayNode) rootNode;

                for (int i = 0; i < arrayNode.size(); i++) {
                    JsonNode node = arrayNode.get(i);
                    if (node.isObject()) {
                        ObjectNode objectNode = (ObjectNode) node;

                        // Check if the ID matches the specified ID
                        if (objectNode.has("id") && objectNode.get("id").asText().equalsIgnoreCase(idToDelete)) {
                            arrayNode.remove(i);
                            i--; // Adjust the index as we removed an element
                            System.out.println("Object with ID '" + idToDelete + "' deleted.");
                            break;
                        }

                    }
                }

                // Write the modified JSON back to the file
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(jFile, arrayNode);

            } else {
                System.out.println("The JSON file does not contain an array.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    public static void getAllIdea() {
//
//        try {
//            File jFile = new File(path);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(jFile);
//
//            if (rootNode.isArray()) {
//                ArrayNode arrayNode = (ArrayNode) rootNode;
//
//                for (int i = 0; i < arrayNode.size(); i++) {
//                    JsonNode node = arrayNode.get(i);
//                    if (node.isObject()) {
//                        ObjectNode objectNode = (ObjectNode) node;
//
//
//                        // Check if the ID matches the specified ID
//                        if (objectNode.has("id")) {
//                            if (!cached_id.contains(objectNode.get("id").asText())) {
//                                cached_id.add(objectNode.get("id").asText());
//                            }
//                        }
//
//                    }
//                }
//
//                // Write the modified JSON back to the file
//                objectMapper.writerWithDefaultPrettyPrinter().writeValue(jFile, arrayNode);
//
//            } else {
//                System.out.println("The JSON file does not contain an array.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static Account findById(String targetId) {
//        try {
//            File jFile = new File(path);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(jFile);
//
//            if (rootNode.isArray()) {
//                ArrayNode arrayNode = (ArrayNode) rootNode;
//
//                for (JsonNode accountNode : arrayNode) {
//                    String accountId = accountNode.get("id").asText();
//                    if (targetId.equals(accountId)) {
//                        // Found the account with the desired ID
//                        return convertJsonToAccount(accountNode, objectMapper);
//                    }
//                }
//
//                System.out.println("Account with ID '" + targetId + "' not found.");
//            } else {
//                System.out.println("The JSON file does not contain an array.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null; // Return null if the account is not found
//    }

    private static Account convertJsonToAccount(JsonNode accountNode, ObjectMapper objectMapper) {
        String id = accountNode.get("id").asText();
        String name = accountNode.get("name").asText();
        String password = accountNode.get("password").asText();

        // Additional properties can be extracted similarly

        // Create an Account object with extracted values
        Account account = new Account(id, name, password);


        return account;

    }

    public static Account updateDb(Account account) {
        try {
            File jFile = new File(path);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jFile);

            if (rootNode.isArray()) {
                ArrayNode arrayNode = (ArrayNode) rootNode;

                for (JsonNode newNode : arrayNode) {
                    String accountId = newNode.get("id").asText();
                    if (account.getId().equals(accountId)) {
                        ((ObjectNode) newNode).put("id", account.getId());
                        ((ObjectNode) newNode).put("name", account.getName());
                        ((ObjectNode) newNode).put("password", account.getPassword());
//
                        ObjectNode objInbox = objectMapper.createObjectNode();
                        objInbox.put("name", account.getInbox().getName());
                        ObjectNode emailsNode = objectMapper.createObjectNode();
                        for (Map.Entry<String, Mail> entry : account.getInbox().getEmails().entrySet()) {
                            emailsNode.put(entry.getKey(), converttoemail(entry.getValue(), objectMapper));
                        }
                        objInbox.put("emails", emailsNode);
                        ((ObjectNode) newNode).put("inbox", objInbox);


                        ObjectNode emailsNode3 = objectMapper.createObjectNode();
                        ObjectNode trashnode = objectMapper.createObjectNode();
                        trashnode.put("name", account.getTrash().getName());

                        for (Map.Entry<String, Mail> entry : account.getTrash().getEmails().entrySet()) {
                            emailsNode3.put(entry.getKey(), converttoemail(entry.getValue(), objectMapper));
                        }
                        trashnode.put("emails", emailsNode3);
                        ((ObjectNode) newNode).put("trash", trashnode);


                        ObjectNode draft = objectMapper.createObjectNode();
                        draft.put("name", account.getDraft().getName());
                        ObjectNode emailsNode1 = objectMapper.createObjectNode();
                        for (Map.Entry<String, Mail> entry : account.getDraft().getEmails().entrySet()) {
                            emailsNode1.put(entry.getKey(), converttoemail(entry.getValue(), objectMapper));
                        }
                        draft.put("emails", emailsNode1);
                        ((ObjectNode) newNode).put("draft", draft);


                        ObjectNode sentfolders = objectMapper.createObjectNode();
                        sentfolders.put("name", account.getSentfolders().getName());
                        ObjectNode emailsNode2 = objectMapper.createObjectNode();
                        for (Map.Entry<String, Mail> entry : account.getSentfolders().getEmails().entrySet()) {
                            emailsNode2.put(entry.getKey(), converttoemail(entry.getValue(), objectMapper));
                        }
                        sentfolders.put("emails", emailsNode2);
                        ((ObjectNode) newNode).put("sentfolders", sentfolders);


                        ObjectNode userfolders = objectMapper.createObjectNode();
                        for (Map.Entry<String, UserFolder> entry : account.getUserFolders().entrySet()) {
                            ObjectNode userfolder = objectMapper.createObjectNode();
                            userfolder.put("id", entry.getValue().getId());
                            userfolder.put("name", entry.getValue().getName());
                            ObjectNode emailsNode7 = objectMapper.createObjectNode();
                            for (Map.Entry<String, Mail> ent : entry.getValue().getEmails().entrySet()) {
                                emailsNode7.put(ent.getKey(), converttoemail(ent.getValue(), objectMapper));
                                userfolder.put(ent.getKey(), emailsNode7);
                            }
                            userfolders.put(entry.getKey(), userfolder);
                        }
                        ((ObjectNode) newNode).put("userFolders", userfolders);


                        ObjectNode contacts = objectMapper.createObjectNode();
                        for (Map.Entry<String, Contact> entry : account.getContactsRegistery().getHash().entrySet()) {
                            ObjectNode con = objectMapper.createObjectNode();
                            con.put("emails", entry.getValue().getEmails());
                            con.put("name", entry.getValue().getName());

                            contacts.put(entry.getKey(), con);
                        }
                        ((ObjectNode) newNode).put("contacts", contacts);


                    }
                }
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(jFile, arrayNode);

                System.out.println("Account with ID '" + account.getId() + "' not found.");
            } else {
                System.out.println("The JSON file does not contain an array.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null if the account is not found
    }

    public static Account findById(String id) {
        try {
            File jFile = new File(path);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jFile);

//            getAllIdea();

            if (rootNode != null && rootNode.isArray()) {


                ArrayNode arrayNode = (ArrayNode) rootNode;

                for (JsonNode accountNode : arrayNode) {


                    Account account = extractAccountFromJson(accountNode, objectMapper);

                    System.out.println(account);

                    managerDB.cache.put(account.getId(), account);

                    return account;
                }


            } else {
                System.out.println("The JSON file does not contain an array.");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }


    public static IMailFolders choose(String name) {
        switch (name) {
            case "inbox":
                return new Inbox(name);
            case "trash":
                return new Trash(name);
            case "draft":
                return new Draft(name);
            case "sentFolders":
                return new SentFolders(name);
            default:
                return new UserFolder(name);
        }
    }


}