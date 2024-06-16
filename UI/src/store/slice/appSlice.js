import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

const initialState = {
    isActiveNavBar: false,
    isSubmitServey: false,
    isLoadRelative: false,
}

export const appSlice = createSlice({
    name: 'app',
    initialState,
    reducers: {
        setIsActiveNavbar: (state, action) => {
            return {
                ...state,
                isActiveNavBar: action.payload
            }
        },
        setIsSubmitServey: (state, action) => {
            return {
                ...state,
                isSubmitServey: action.payload
            }
        },
        setIsLoadRelative: (state, action) => {
            return {
                ...state,
                isLoadRelative: action.payload
            }
        }
    },
    extraReducers: (builder) => { }
})

export const { setIsActiveNavbar, setIsSubmitServey, setIsLoadRelative
} = appSlice.actions

export default appSlice.reducer