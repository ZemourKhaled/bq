import * as angular from "angular";

export class CompteService {


    constructor(private $http) {
    }

    getUserCompte(id){
        return this.$http
            .get(`/api/comptes/${id}`, {
                credentials: 'include',
            })
            .then(resp => {
                if (resp.status === 200) {
                    return resp.data;
                }
            })
            .catch((e) => {
            });
    }

    loadAllUserComptes(id){
        return this.$http.get('/api/comptes', {
            credentials: 'include',
        });
    }

}

export default "CompteService";
