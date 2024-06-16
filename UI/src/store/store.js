import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { persistStore, persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
import userSlice from "./slice/userSlice";
import accountSlice from "./slice/accountSlice";
import appSlice from "./slice/appSlice";
import paymentSlice from "./slice/paymentSlice";
import surveySlice from "./slice/surveySlice";
import questionSubmitSlice from "./slice/questionSubmitSlice";
import relativeSlice from "./slice/relativesSlice";


const persistConfig = {
    key: 'root',
    storage,
}

const rootReducer = combineReducers({
    user: userSlice,
    account: accountSlice,
    app: appSlice,
    payments: paymentSlice,
    surveys: surveySlice,
    questionsSubmit: questionSubmitSlice,
    relatives: relativeSlice
})

const persistedReducer = persistReducer(persistConfig, rootReducer)

export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false
    }),
})

export const persistor = persistStore(store)