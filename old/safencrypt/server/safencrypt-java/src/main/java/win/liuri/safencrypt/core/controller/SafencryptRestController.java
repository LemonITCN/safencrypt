package win.liuri.safencrypt.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import win.liuri.safencrypt.core.bean.ClientInfo;
import win.liuri.safencrypt.core.exception.FlagInvalidException;
import win.liuri.safencrypt.core.service.SafencryptRestService;

@RestController
public class SafencryptRestController {

    @Autowired
    private SafencryptRestService safencryptRestService;

    @RequestMapping("/safencrypt/apply-public-key")
    public Object applyPublicKey() {
        return safencryptRestService.applyPublicKey();
    }

    @RequestMapping(value = "/safencrypt/sign-up-client", method = RequestMethod.POST)
    public Object signUpClient(@RequestBody ClientInfo identifier) throws FlagInvalidException {
        return safencryptRestService.signUpClient(identifier.getIdentifier());
    }

}
