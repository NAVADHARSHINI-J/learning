import { configureStore } from "@reduxjs/toolkit";
import ProfileSlice from "./ProfileSlice"

const store = configureStore({
    reducer:{
        profile : ProfileSlice
    }
})
export default store;