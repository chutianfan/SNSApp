package com.gitrose.mobile.download.video;

import java.lang.ref.WeakReference;

public class RequestHandle {
    private final WeakReference<DownLoadRunnable<?>> request;

    public RequestHandle(DownLoadRunnable<?> request) {
        this.request = new WeakReference(request);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        DownLoadRunnable<?> _request = (DownLoadRunnable) this.request.get();
        return _request == null || _request.cancel(mayInterruptIfRunning);
    }

    public boolean pause() {
        DownLoadRunnable<?> _request = (DownLoadRunnable) this.request.get();
        return _request == null || _request.pause();
    }

    public boolean isFinished() {
        DownLoadRunnable<?> _request = (DownLoadRunnable) this.request.get();
        return _request == null || _request.isDone();
    }

    public boolean isCancelled() {
        DownLoadRunnable<?> _request = (DownLoadRunnable) this.request.get();
        return _request == null || _request.isCancelled();
    }

    public boolean shouldBeGarbageCollected() {
        boolean should = isCancelled() || isFinished();
        if (should) {
            this.request.clear();
        }
        return should;
    }
}
