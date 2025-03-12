package Service;
import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.List;
import java.util.Optional;


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

    public Optional<Message> viewMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public Optional<Message> deleteMessageById(int id){
        Optional<Message> messageToDelete = messageDAO.getMessageById(id);
        if(messageToDelete.isPresent()){
            messageDAO.deleteMessageById(id);
        }
        return messageToDelete;
    }
    public Optional<Message> updateMessageById(int id, String text){
        Optional<Message> messageToUpdate = messageDAO.getMessageById(id);

        int message_text_length = text.length();
        boolean messageLengthIsValid = message_text_length > 0 && message_text_length < 255;
        
        if(messageToUpdate.isPresent() && messageLengthIsValid){
            messageDAO.updateMessageById(id, text);
            Message updatedMessage = messageToUpdate.get();
            updatedMessage.setMessage_text(text);
            return Optional.of(updatedMessage);
        }
        return Optional.empty();

    }   

};