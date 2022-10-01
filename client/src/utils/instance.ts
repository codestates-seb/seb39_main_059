import axios from 'axios'

export const axiosInstance = axios.create({
  baseURL: process.env.APP_API,
})
