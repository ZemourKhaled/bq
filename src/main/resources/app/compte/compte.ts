import {bootstrap, module} from "angular";
import Compte from "./compte/compte";
import uirouter from "@uirouter/angularjs";
import CompteComponent from "./compte/compte";
import {default as userServiceName, UserService} from "../service/UserService";
import {default as compteServiceName, CompteService} from "../service/CompteService";

module('compte', [
    uirouter,
])
    .component(Compte.name, Compte.component)
    .service(userServiceName, UserService)
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {


        const compte = {
            name: "compte",
            state: {
                url: "/compte",
                views: {
                    'main@': {
                        component: CompteComponent.name
                    }
                }
            }
        };


        $urlRouterProvider.otherwise("/compte");

        $stateProvider
            .state(compte.name, compte.state)

    }])
;
bootstrap(document.body, ['compte']);
