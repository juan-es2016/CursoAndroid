// Generated code from Butter Knife. Do not modify!
package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetalleLugarFragment_ViewBinding implements Unbinder {
  private DetalleLugarFragment target;

  private View view2131624111;

  private View view2131624110;

  @UiThread
  public DetalleLugarFragment_ViewBinding(final DetalleLugarFragment target, View source) {
    this.target = target;

    View view;
    target.titulo_lugar = Utils.findRequiredViewAsType(source, R.id.titulo_lugar, "field 'titulo_lugar'", TextView.class);
    target.nombre_cajero = Utils.findRequiredViewAsType(source, R.id.nombre_cajero, "field 'nombre_cajero'", TextView.class);
    target.direccion_cajero = Utils.findRequiredViewAsType(source, R.id.direccion_cajero, "field 'direccion_cajero'", TextView.class);
    target.horario_atencion = Utils.findRequiredViewAsType(source, R.id.horario_atencion, "field 'horario_atencion'", TextView.class);
    target.sitio_web = Utils.findRequiredViewAsType(source, R.id.url, "field 'sitio_web'", Button.class);
    view = Utils.findRequiredView(source, R.id.como_llegar, "field 'b_como_llegar' and method 'onViewClicked'");
    target.b_como_llegar = Utils.castView(view, R.id.como_llegar, "field 'b_como_llegar'", Button.class);
    view2131624111 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mostrar_en_mapa, "field 'mostrarEnMapa' and method 'onViewClicked'");
    target.mostrarEnMapa = Utils.castView(view, R.id.mostrar_en_mapa, "field 'mostrarEnMapa'", Button.class);
    view2131624110 = view;
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
    DetalleLugarFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titulo_lugar = null;
    target.nombre_cajero = null;
    target.direccion_cajero = null;
    target.horario_atencion = null;
    target.sitio_web = null;
    target.b_como_llegar = null;
    target.mostrarEnMapa = null;

    view2131624111.setOnClickListener(null);
    view2131624111 = null;
    view2131624110.setOnClickListener(null);
    view2131624110 = null;
  }
}
