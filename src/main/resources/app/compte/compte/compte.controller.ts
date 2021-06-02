import {UserService, default as userServiceName} from "../../service/UserService";
import {CompteService, default as compteServiceName} from "../../service/CompteService";

export default class indexCtrl {

    private static readonly $inject=[
        userServiceName,
        "$sce",
        compteServiceName,
        "$state"
    ]
    private comptes: Array<any>;
    private name : string;
    private montent : string;

    constructor(private $state, private $sce, private userService:UserService, private compteService:CompteService) {
    }

    $onInit() {
        this.userService.getCurrentUser()
            .then((response) => {
                this.loadComptes(response.id);
            });
    }

    loadComptes(id) {
        return this.compteService.loadAllUserComptes(id)
            .then((response) => {
                this.comptes = response.data;
                return response;
            });
    }

}
