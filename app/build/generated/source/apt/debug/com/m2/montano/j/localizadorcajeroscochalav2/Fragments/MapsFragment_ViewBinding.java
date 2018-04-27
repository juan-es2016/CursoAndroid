// Generated code from Butter Knife. Do not modify!
package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
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

public class MapsFragment_ViewBinding implements Unbinder {
  private MapsFragment target;

  private View view2131624125;

  private View view2131624132;

  @UiThread
  public MapsFragment_ViewBinding(final MapsFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.pasos, "field 'pasos' and method 'onViewClicked'");
    target.pasos = Utils.castView(view, R.id.pasos, "field 'pasos'", Button.class);
    view2131624125 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvDistance = Utils.findRequiredViewAsType(source, R.id.tvDistance, "field 'tvDistance'", TextView.class);
    target.imagedistancia = Utils.findRequiredViewAsType(source, R.id.imagedistancia, "field 'imagedistancia'", ImageView.class);
    target.tvDuration = Utils.findRequiredViewAsType(source, R.id.tvDuration, "field 'tvDuration'", TextView.class);
    target.imagetiempo = Utils.findRequiredViewAsType(source, R.id.imagetiempo, "field 'imagetiempo'", ImageView.class);
    target.linearLayoutTiempoDistancia = Utils.findRequiredViewAsType(source, R.id.tiempo_distancia, "field 'linearLayoutTiempoDistancia'", LinearLayout.class);
    target.recyclerViewPasos = Utils.findRequiredViewAsType(source, R.id.recycler_view_pasos, "field 'recyclerViewPasos'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.mas_informacion, "field 'masInformacion' and method 'onViewClicked'");
    target.masInformacion = Utils.castView(view, R.id.mas_informacion, "field 'masInformacion'", Button.class);
    view2131624132 = view;
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
    MapsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pasos = null;
    target.tvDistance = null;
    target.imagedistancia = null;
    target.tvDuration = null;
    target.imagetiempo = null;
    target.linearLayoutTiempoDistancia = null;
    target.recyclerViewPasos = null;
    target.masInformacion = null;

    view2131624125.setOnClickListener(null);
    view2131624125 = null;
    view2131624132.setOnClickListener(null);
    view2131624132 = null;
  }
}
