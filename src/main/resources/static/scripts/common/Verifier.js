export default class Verifier {

    constructor() {
        throw new Error('Verifier can\'t be instantiated');
    }

    static verifyEmail(emailToVerify) {
        const EMAIL_REG_EXP = /^(([^<>()\[\]\.,;:\s@"]+(\.[^<>()\[\]\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (EMAIL_REG_EXP.test(String(emailToVerify).toLowerCase())) {
            return emailToVerify;
        } else {
            throw new Error('Incorrect email input')
        }
    }

    static verifyUsername(usernameToVerify) {
        const usernameRegExp = /^\w{6,32}$/i;
        if (usernameRegExp.test(usernameToVerify)) {
            return usernameToVerify;
        } else {
            throw new Error('Incorrect username input')
        }
    }

    static verifyPassword(passwordToVerify) {
        const PASSWORD_REG_EXP = /^\w{6,32}$/;
        if (PASSWORD_REG_EXP.test(passwordToVerify)) {
            return passwordToVerify;
        } else {
            throw new Error('Incorrect password input');
        }
    }

    static verifyRegistrationData(registrationDataToVerify) {
        this.verifyEmail(registrationDataToVerify.getEmail());
        this.verifyUsername(registrationDataToVerify.getUsername());
        this.verifyPassword(registrationDataToVerify.getPassword());
    }

    static verifyTime(timeToVerify) {
        if (timeToVerify.length < 5) {
            throw new Error('Incorrect time input');
        }
        return timeToVerify;
    }

    static verifyNote(noteToVerify) {
        if (noteToVerify.length === 0) {
            throw new Error('Incorrect note input');
        }
        return noteToVerify;
    }

    static verifyRowData(rowDataToVerify) {
        this.verifyTime(rowDataToVerify.getTime());
        this.verifyNote(rowDataToVerify.getNote());
    }
}