// Generated code from Butter Knife. Do not modify!
package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ErrorConexionFragment_ViewBinding implements Unbinder {
  private ErrorConexionFragment target;

  private View view2131624115;

  @UiThread
  public ErrorConexionFragment_ViewBinding(final ErrorConexionFragment target, View source) {
    this.target = target;

    View view;
    target.noMessagesIcon = Utils.findRequiredViewAsType(source, R.id.noMessagesIcon, "field 'noMessagesIcon'", ImageView.class);
    target.noMessagesText = Utils.findRequiredViewAsType(source, R.id.noMessagesText, "field 'noMessagesText'", TextView.class);
    target.noMessages = Utils.findRequiredViewAsType(source, R.id.noMessages, "field 'noMessages'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.volver_a_intentar, "field 'volverAIntentar' and method 'onViewClicked'");
    target.volverAIntentar = Utils.castView(view, R.id.volver_a_intentar, "field 'volverAIntentar'", Button.class);
    view2131624115 = view;
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
    ErrorConexionFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.noMessagesIcon = null;
    target.noMessagesText = null;
    target.noMessages = null;
    target.volverAIntentar = null;

    view2131624115.setOnClickListener(null);
    view2131624115 = null;
  }
}
