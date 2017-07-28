package org.cf.card;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.cf.card.model.User;
import org.cf.card.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Dell on 10/10/2014.
 */
public class UserTests extends BaseTest{

    @Autowired
    private UserService userService;

    private User constructLogin(){
        User login = new User();
        login.setRoleId(1);
        login.setLoginCode("XYZ1223OZ");
        return login;
    }

   @Test
    public void addLoginTest(){

       User expected = constructLogin();
       User received = userService.save(expected);
       assertThat(received, is(expected));
       userService.deleteById(received.getId());

   }

    //@Test
    public void getLoginByTypeTest() {
//        User expected = constructLogin();
//        userService.save(expected);
//        List<User> received = userService.getByType(expected.getType());
//        assertThat(received, hasItem(expected));
//        userService.deleteById(expected.getId());
    }

    @Test
    public void getLoginByLoginCode(){
        User expected = constructLogin();
        userService.save(expected);
        List<User> received = userService.getByLoginCode(expected.getLoginCode());
        assertThat(received, hasItem(expected));
        userService.deleteById(expected.getId());
    }

    @Test
    public void deleteLoginTest(){
        User created = userService.save(constructLogin());
        userService.deleteById(created.getId());
        User received = userService.findOne(created.getId());
        assertThat(received, is(nullValue()));
    }
//
//    @Test
//    public void updateLoginTest(){
//        Login expected = constructLogin();
//        Login received = userService.updateLogin(expected);
//        assertThat(received, is(expected));
//
//    }
}
