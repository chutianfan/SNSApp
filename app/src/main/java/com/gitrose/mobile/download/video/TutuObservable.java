package com.gitrose.mobile.download.video;

import android.database.Observable;

public class TutuObservable<T> extends Observable<T> {
    public void registerObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (this.mObservers) {
            if (!this.mObservers.contains(observer)) {
                this.mObservers.add(observer);
            }
        }
    }

    public void unregisterObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (this.mObservers) {
            int index = this.mObservers.indexOf(observer);
            if (index != -1) {
                this.mObservers.remove(index);
            }
        }
    }
}
