import { createSlice } from "@reduxjs/toolkit";

const initialState = {}

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        loadUser: (state, action) => {
            return action.payload
        },
        clearUser: () => {
            return initialState
        }
    },
    extraReducers: (builder) => {}
})

export const { loadUser, clearUser } = userSlice.actions

export default userSlice.reducer