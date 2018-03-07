package cn.lemonit.safencrypt.springboot.core.controller;

import cn.lemonit.safencrypt.springboot.core.bean.ClientInfo;
import cn.lemonit.safencrypt.springboot.core.exception.FlagInvalidException;
import cn.lemonit.safencrypt.springboot.core.service.SafencryptRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
