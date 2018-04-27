// Generated code from Butter Knife. Do not modify!
package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TodosCajerosFragment_ViewBinding implements Unbinder {
  private TodosCajerosFragment target;

  private View view2131624110;

  @UiThread
  public TodosCajerosFragment_ViewBinding(final TodosCajerosFragment target, View source) {
    this.target = target;

    View view;
    target.list = Utils.findRequiredViewAsType(source, R.id.list, "field 'list'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.mostrar_en_mapa, "field 'mostrarEnMapa' and method 'onViewClicked'");
    target.mostrarEnMapa = Utils.castView(view, R.id.mostrar_en_mapa, "field 'mostrarEnMapa'", Button.class);
    view2131624110 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    TodosCajerosFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.list = null;
    target.mostrarEnMapa = null;

    view2131624110.setOnClickListener(null);
    view2131624110 = null;
  }
}
