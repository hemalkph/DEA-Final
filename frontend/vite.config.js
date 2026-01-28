import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig({
    build: {
        rollupOptions: {
            input: {
                main: resolve(__dirname, 'index.html'),
                login: resolve(__dirname, 'login.html'),
                register: resolve(__dirname, 'register.html'),
                properties: resolve(__dirname, 'properties.html'),
                view_property: resolve(__dirname, 'view-property.html'),
            },
        },
    },
});
