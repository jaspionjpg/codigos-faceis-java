package cacheservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class HttpSessionService {
    /*
    httpSession..setAttribute("key", "value")
     */
    @Autowired
    private HttpSession httpSession;

    public boolean hasAttribute(String attributeName) {

        if (httpSession == null) {
            return false;
        }

        Object attribute = httpSession.getAttribute(attributeName);

        return attribute != null;
    }

    public HttpSession session() {
        return httpSession;
    }

}
