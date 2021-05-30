import {bootstrap, module} from "angular";
import Login from "./login/login";
//
import uirouter from "@uirouter/angularjs";
import LoginComponent from './login/login'
import {default as userServiceName, UserService} from "../service/UserService";

module('login', [
    uirouter,
])
    .component(Login.name, Login.component)
    .service(userServiceName, UserService)
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {


        const login = {
            name: "login",
            state: {
                url: "/login",
                views: {
                    'main@': {
                        component: LoginComponent.name
                    }
                }
            }
        };



        $urlRouterProvider.otherwise("/login");

        $stateProvider
            .state(login.name, login.state)

    }])
;
bootstrap(document.body, ['login']);
