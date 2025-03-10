package Service;
import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;


public class MessageService{
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public boolean messageIsValid(Message message){
        int message_text_length = message.getMessage_text().length();

        boolean messageLengthIsValid = message_text_length > 0 && message_text_length < 255;
        boolean accountIsValid = accountDAO.getAccountById(message.getPosted_by()) != null;
        return messageLengthIsValid && accountIsValid;

    }
    public Message createMessage(Message message){
        if (messageIsValid(message)){
            return messageDAO.insertNewMessage(message);
        }
        return null;
    }
    public List<Message> viewAllMessages(){
        return messageDAO.getAllMessages();
    }
};