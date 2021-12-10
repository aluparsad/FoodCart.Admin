package com.icmi.foodcartadmin.NewProduct;

import com.icmi.foodcartadmin.Model.Food;

public interface Contractor {
    interface View {
        void ShowLoading();
        void HideLoading();
        void onSuccess();
        void onError();
    }

    interface Presenter {
        void uploadFoodItem(Food food);
    }
}
