import {bootstrap, module} from "angular";
import Accueil from "./accueil/accueil";
import uirouter from "@uirouter/angularjs";
import AccueilComponent from "./accueil/accueil";
import {default as userServiceName, UserService} from "../service/UserService";

module('accueil', [
    uirouter,
])
    .component(Accueil.name, Accueil.component)
    .service(userServiceName, UserService)
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {


        const accueil = {
            name: "accueil",
            state: {
                url: "/accueil",
                views: {
                    'main@': {
                        component: AccueilComponent.name
                    }
                }
            }
        };




        $urlRouterProvider.otherwise("/accueil");

        $stateProvider
            .state(accueil.name, accueil.state)

    }])
;
bootstrap(document.body, ['accueil']);
