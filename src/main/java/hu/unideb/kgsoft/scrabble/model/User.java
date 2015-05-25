package hu.unideb.kgsoft.scrabble.model;

@SuppressWarnings("unused")
public class User {    
    
    private final String USERNAME;    
    private final String PASSWD;
    
    public User(String uSERNAME, String pASSWD) {
        USERNAME = uSERNAME;
        PASSWD = pASSWD;
    }    
}
