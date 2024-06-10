import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    isActiveNavBar: false,
    isSubmitServey: false,
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
        }
    },
    extraReducers: (builder) => {}
})

export const { setIsActiveNavbar, setIsSubmitServey } = appSlice.actions

export default appSlice.reducer