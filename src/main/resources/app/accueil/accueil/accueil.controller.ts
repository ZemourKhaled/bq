import {UserService, default as userServiceName} from "../../service/UserService";

export default class accueilCtrl {

    private static readonly $inject = ["$state", userServiceName];

    constructor(private $state, private userService:UserService) {
    }


}
