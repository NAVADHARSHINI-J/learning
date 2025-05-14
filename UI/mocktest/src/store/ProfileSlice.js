import { createSlice } from "@reduxjs/toolkit";

const ProfileSlice = createSlice({
    name : "profile",
    initialState : {
        profile : []
    },
    reducers : {
        setProfile(state,action){
            state.profile = action.payload.profile
        }
    }
});
export const {setProfile} = ProfileSlice.actions;
export default ProfileSlice.reducer