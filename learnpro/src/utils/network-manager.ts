/* eslint-disable @typescript-eslint/no-explicit-any */
import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";

axios.defaults.baseURL = import.meta.env.VITE_API_URL;

export function getRequest<T>(
  endPoint: string,
  parameters: any = "",
  url = "",
  header: any = axios.defaults.headers
) {
  if (url !== "") {
    axios.defaults.baseURL = url;
  }
  axios.defaults.headers = {
    ...header,
  };

  return new Promise<AxiosResponse<T, any>>((resolve, reject) => {
    axios
      .get(endPoint, {
        params:
          parameters === undefined || parameters === null ? "" : parameters,
      })
      .then(function (response: AxiosResponse<T>) {
        resolve(response);
      })
      .catch(function (error: AxiosError) {
        reject(error);
      });
  });
}

export function postRequest<T>(
  endPoint: string,
  parameters: any = {},
  header: any = axios.defaults.headers,
  requestConfig: AxiosRequestConfig | undefined = undefined
) {
  // set this to token returned by server after user logs in
  axios.defaults.headers = {
    ...header,
  };

  return new Promise<AxiosResponse<T>>((resolve, reject) => {
    axios
      .post(endPoint, parameters, requestConfig)
      .then((response: AxiosResponse<T>) => {
        resolve(response);
      })
      .catch((error: AxiosError) => {
        reject(error);
      });
  });
}
