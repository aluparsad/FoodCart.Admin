package com.icmi.foodcartadmin.NewProduct;

import com.icmi.foodcartadmin.Model.Food;
import com.icmi.foodcartadmin.Utils.Database;


public class Presenter implements Contractor.Presenter {
    private Contractor.View mView;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void uploadFoodItem(Food food) {
        mView.ShowLoading();

        Database db = new Database();
        db.uploadFood(food, result -> {
            if (result) mView.onSuccess();
            else mView.onError();
        });
    }
}
