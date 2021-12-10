package com.icmi.foodcartadmin.Utils;


public interface Listener {

    enum StatePayment {
        PAYED, FAILED;
    }

    interface OnResultListener<T> {
        void onResult(T result);
    }

    interface Observer<T> {
        void onChangeOccured(T changedData);
    }

    interface OnItemClickListener {
        void onClick(Object item);
    }

}
