package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService{
    private AccountDAO accountDAO;

    public AccountService (){
        this.accountDAO = new AccountDAO();
    }
    public boolean userNameIsValid(String username){
        //check if username is not empty
        boolean userNameNotEmpty = username.length() != 0;
        boolean userNameAvailable = this.accountDAO.getAccountByUserName(username) == null;

        return userNameNotEmpty && userNameAvailable;

    }

    public boolean passwordIsValid(String password){
        return password.length() >= 4;
    }

    public Account registerNewAccount(Account acc){
        boolean usernameIsValid = userNameIsValid(acc.getUsername());
        boolean passwordIsValid = passwordIsValid(acc.getPassword());
        if(usernameIsValid && passwordIsValid){
            return accountDAO.insertAccount(acc);
        }
        return null;        
    }
}