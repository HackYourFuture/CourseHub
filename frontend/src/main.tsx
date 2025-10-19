import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import App from "./App";
import "./index.css";
import { ConfigContext, config } from './ConfigContext';

const queryClient = new QueryClient();

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <ConfigContext.Provider value={config}>
            <QueryClientProvider client={queryClient}>
                <App/>
            </QueryClientProvider>
        </ConfigContext.Provider>
    </StrictMode>
);
