// Generated code from Butter Knife. Do not modify!
package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeMapsFragment_ViewBinding implements Unbinder {
  private HomeMapsFragment target;

  private View view2131624117;

  @UiThread
  public HomeMapsFragment_ViewBinding(final HomeMapsFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.textoAñadir, "field 'textoAnadir' and method 'onViewClicked'");
    target.textoAnadir = Utils.castView(view, R.id.textoAñadir, "field 'textoAnadir'", TextView.class);
    view2131624117 = view;
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
    HomeMapsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textoAnadir = null;

    view2131624117.setOnClickListener(null);
    view2131624117 = null;
  }
}
