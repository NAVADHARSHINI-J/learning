import axios from "axios"
import { setProfile} from "../ProfileSlice"

const fetchProfile = () =>async (dispatch) =>{
    let token = localStorage.getItem("token")
    let user = localStorage.getItem("username")
    let headers = {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    }
    try {
        const resp = await axios.get(`http://localhost:8081/api/admin/byuser/${user}`, headers);
        dispatch(setProfile({ profile: resp.data }));
    } catch (error) {
        console.log(error);
    }
}

export default fetchProfile