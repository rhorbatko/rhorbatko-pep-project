package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postNewAccountHandler);
        app.post("/login", this::accountLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    private void postNewAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acct = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerNewAccount(acct);
        if(addedAccount != null){
            ctx.json(addedAccount).status(200);
        }else{
            ctx.status(400);
        }
    }
    private void accountLoginHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acct = mapper.readValue(ctx.body(), Account.class);
        Account accountToLogin = accountService.logIntoAccount(acct.getUsername(), acct.getPassword());
        if(accountToLogin != null){
            ctx.json(accountToLogin).status(200);
        }else{
            ctx.status(401);
        }
    }
    private void postMessageHandler(Context ctx)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message messageToPost = messageService.createMessage(message);
        if(messageToPost != null){
            ctx.json(messageToPost).status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx)throws JsonProcessingException{

        List<Message> allMessages = messageService.viewAllMessages();
        ctx.json(allMessages).status(200);
    }


}