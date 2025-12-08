package org.example.session_manager;

import org.example.PropertiesHandler;
import org.example.enums.FrontEndTypeEnum;
import org.example. exceptions.PropertyException;
import org.example.exceptions.ResourceNotFoundException;
import org.example.model. user.User;

import java. util.ArrayList;
import java. util.List;

public class SessionManager {
    private static SessionManager instance;
    private final List<Session> activeSessions;
    private String currentTokenKey; // Token della sessione corrente

    private SessionManager() {
        activeSessions = new ArrayList<>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private Session getSession(User user) {
        for (Session s : activeSessions) {
            if (s.getUser().equals(user)) {
                return s;
            }
        }
        return null;
    }

    private Session getSession(String tokenKey) {
        for (Session s : activeSessions) {
            if (s.getToken().getKey().equals(tokenKey)) {
                return s;
            }
        }
        return null;
    }

    public User getSessionUserByTokenKey(String tokenKey) {
        Session s = getSession(tokenKey);
        if (s == null) {
            return null;
        }
        return s.getUser();
    }

    public String getSessionTokenKeyByUser(User user) throws ResourceNotFoundException, PropertyException {
        Session s = getSession(user);
        if (s == null) {
            FrontEndTypeEnum frontEndType = FrontEndTypeEnum.getFrontEndTypeByValue(PropertiesHandler.getInstance(). getProperty("front_end_type"));
            s = new Session(user, frontEndType);
            activeSessions.add(s);
        }
        return s.getToken().getKey();
    }

    // ==================== NUOVI METODI ====================

    /**
     * Imposta il token della sessione corrente.
     * Chiamato dopo il login.
     * @param tokenKey il token della sessione
     */
    public void setCurrentTokenKey(String tokenKey) {
        this.currentTokenKey = tokenKey;
    }

    /**
     * Recupera il token della sessione corrente.
     * @return il token corrente, null se non c'è sessione attiva
     */
    public String getCurrentTokenKey() {
        return currentTokenKey;
    }

    /**
     * Recupera l'utente della sessione corrente.
     * @return l'User corrente, null se non c'è sessione attiva
     */
    public User getSessionUser() {
        if (currentTokenKey == null || currentTokenKey.isEmpty()) {
            return null;
        }
        return getSessionUserByTokenKey(currentTokenKey);
    }

    /**
     * Verifica se c'è una sessione attiva.
     * @return true se c'è una sessione attiva
     */
    public boolean hasActiveSession() {
        return currentTokenKey != null && getSessionUser() != null;
    }

    /**
     * Effettua il logout della sessione corrente.
     */
    public void logout() {
        if (currentTokenKey != null) {
            Session s = getSession(currentTokenKey);
            if (s != null) {
                activeSessions.remove(s);
            }
            currentTokenKey = null;
        }
    }

    /**
     * Effettua il logout di un utente specifico.
     * @param user l'utente da disconnettere
     */
    public void logout(User user) {
        Session s = getSession(user);
        if (s != null) {
            if (s.getToken(). getKey(). equals(currentTokenKey)) {
                currentTokenKey = null;
            }
            activeSessions.remove(s);
        }
    }
}