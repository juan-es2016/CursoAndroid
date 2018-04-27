// Generated code from Butter Knife. Do not modify!
package com.m2.montano.j.localizadorcajeroscochalav2.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MarcarPuntoMapaActivity_ViewBinding implements Unbinder {
  private MarcarPuntoMapaActivity target;

  private View view2131624068;

  private View view2131624072;

  @UiThread
  public MarcarPuntoMapaActivity_ViewBinding(MarcarPuntoMapaActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MarcarPuntoMapaActivity_ViewBinding(final MarcarPuntoMapaActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.toolbar, "field 'toolbar' and method 'onViewClicked'");
    target.toolbar = Utils.castView(view, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    view2131624068 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.guardar_punto_seleccionado, "field 'guardarPuntoSeleccionado' and method 'onViewClicked'");
    target.guardarPuntoSeleccionado = Utils.castView(view, R.id.guardar_punto_seleccionado, "field 'guardarPuntoSeleccionado'", Button.class);
    view2131624072 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MarcarPuntoMapaActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.guardarPuntoSeleccionado = null;

    view2131624068.setOnClickListener(null);
    view2131624068 = null;
    view2131624072.setOnClickListener(null);
    view2131624072 = null;
  }
}
