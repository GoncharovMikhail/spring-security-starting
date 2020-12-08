/**
 * An entity class, representing row table's data: day, time, note and accessible.
 * Also proceed some verifications.
 */

export default class VerifiedRowData {

    #day;

    #time;

    #note;

    #accessible;

    constructor(day, time, note, accessible) {
        this.#day = day;
        this.#time = this.#verifyTime(time);
        this.#note = this.#verifyNote(note);
        this.#accessible = accessible;
    }

    #verifyTime(time) {
        if (time.length < 5) {
            throw new Error('Incorrect time input');
        }
        return time;
    }

    #verifyNote(note) {
        if (note.length === 0) {
            throw new Error('Incorrect note input');
        }
        return note;
    }

    getDay() {
        return this.#day;
    }

    getTime() {
        return this.#time;
    }

    getNote() {
        return this.#note;
    }

    isAccessible() {
        return this.#accessible;
    }

    equals(otherData) {
        if (!(otherData instanceof VerifiedRowData)) {
            return false;
        } else {
            if (this.getDay() === otherData.getDay() &&
                this.getTime() === otherData.getTime() &&
                this.getNote() === otherData.getNote() &&
                this.isAccessible() === otherData.isAccessible()) {
                return true;
            }
        }
    }
}