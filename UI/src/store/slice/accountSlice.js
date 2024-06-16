/* eslint-disable no-useless-catch */
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import newRequest from "../../untils/request"
import { useNavigate } from "react-router-dom"

const initialState = {
    username: '',
    accessToken: '',
    // refreshToken: ''
}

export const fetchLogin = createAsyncThunk(
    'account/fetchLogin',
    async ({ username, password }, { rejectWithValue }) => {
        try {
            const response = await newRequest.post('/auth/login', {
                username, password
            });
            console.log(response.data);
            return response.data;
        } catch (error) {
            return rejectWithValue(new Error(error.response.data));
        }
    }
)

export const accountSlice = createSlice({
    name: 'account',
    initialState,
    reducers: {
        login: (state, action) => {
            console.log(action.payload)
            return {
                ...action.payload
            }
        },
        logout: () => {
            localStorage.removeItem('accessToken')
            return initialState
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchLogin.fulfilled, (state, action) => {
                return {
                    username: action.payload.user.username,
                    accessToken: action.payload.accessToken
                }
            })
    }
})

export default accountSlice.reducer

export const { login, logout } = accountSlice.actions
