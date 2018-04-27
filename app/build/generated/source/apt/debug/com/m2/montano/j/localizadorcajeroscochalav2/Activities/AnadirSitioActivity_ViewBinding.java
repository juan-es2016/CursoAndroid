// Generated code from Butter Knife. Do not modify!
package com.m2.montano.j.localizadorcajeroscochalav2.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AnadirSitioActivity_ViewBinding implements Unbinder {
  private AnadirSitioActivity target;

  private View view2131624069;

  private View view2131624084;

  private View view2131624086;

  @UiThread
  public AnadirSitioActivity_ViewBinding(AnadirSitioActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AnadirSitioActivity_ViewBinding(final AnadirSitioActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.ic_enviar, "field 'icEnviar' and method 'onViewClicked'");
    target.icEnviar = Utils.castView(view, R.id.ic_enviar, "field 'icEnviar'", ImageView.class);
    view2131624069 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.nombreCajero = Utils.findRequiredViewAsType(source, R.id.nombre_cajero, "field 'nombreCajero'", EditText.class);
    target.direccion = Utils.findRequiredViewAsType(source, R.id.direccion, "field 'direccion'", EditText.class);
    target.selecionarLugar = Utils.findRequiredViewAsType(source, R.id.selecionar_lugar, "field 'selecionarLugar'", FrameLayout.class);
    view = Utils.findRequiredView(source, R.id.horario, "field 'horario' and method 'onViewClicked'");
    target.horario = Utils.castView(view, R.id.horario, "field 'horario'", Button.class);
    view2131624084 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.sitioWeb = Utils.findRequiredViewAsType(source, R.id.sitio_web, "field 'sitioWeb'", EditText.class);
    view = Utils.findRequiredView(source, R.id.nombreEF, "field 'nombreEF' and method 'onViewClicked'");
    target.nombreEF = Utils.castView(view, R.id.nombreEF, "field 'nombreEF'", Button.class);
    view2131624086 = view;
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
    AnadirSitioActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.icEnviar = null;
    target.nombreCajero = null;
    target.direccion = null;
    target.selecionarLugar = null;
    target.horario = null;
    target.sitioWeb = null;
    target.nombreEF = null;

    view2131624069.setOnClickListener(null);
    view2131624069 = null;
    view2131624084.setOnClickListener(null);
    view2131624084 = null;
    view2131624086.setOnClickListener(null);
    view2131624086 = null;
  }
}
