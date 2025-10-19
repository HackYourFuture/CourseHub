import {createContext, useContext} from "react";

export type Config = {
    backendUrl: string;
};

export const config = loadConfig();
export const ConfigContext = createContext<Config>(config);

function loadConfig(): Config {
    if ((window as any).__CONFIG__) {
        return (window as any).__CONFIG__;
    }
    return {
        backendUrl: 'http://localhost:8080'
    };
}

export const useConfig = () => useContext(ConfigContext);
