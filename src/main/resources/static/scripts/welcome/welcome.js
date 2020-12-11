import WelcomePageManager from "./components/WelcomePageManager.js";

const welcomePageManager = new WelcomePageManager();

export function onSearchButtonClicked() {
    welcomePageManager.onSearchButtonClicked();
}