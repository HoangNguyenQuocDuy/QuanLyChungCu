/* eslint-disable no-useless-catch */
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import newRequest from "../../untils/request"

const initialState = []

export const fetchRelatives = createAsyncThunk(
    'relative/fetchLogin',
    async ({ userId, accessToken }, { rejectWithValue }) => {
        try {
            console.log(userId, " ", accessToken)
            const response = await newRequest.get(`/relatives/?list=true&userId=${userId}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
            console.log(response.data);
            return response.data;
        } catch (error) {
            return rejectWithValue(new Error(error.response.data));
        }
    }
)

export const relativeSlice = createSlice({
    name: 'relative',
    initialState,
    reducers: {
        deleteRelative: (state, action) => {
            return state.filter(r => r.id !== action.payload)
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchRelatives.fulfilled, (state, action) => {
                return action.payload
            })
    }
})

export default relativeSlice.reducer

export const { deleteRelative } = relativeSlice.actions
