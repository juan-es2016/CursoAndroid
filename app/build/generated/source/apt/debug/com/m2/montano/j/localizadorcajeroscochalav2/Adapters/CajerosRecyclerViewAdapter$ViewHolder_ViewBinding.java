// Generated code from Butter Knife. Do not modify!
package com.m2.montano.j.localizadorcajeroscochalav2.Adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CajerosRecyclerViewAdapter$ViewHolder_ViewBinding implements Unbinder {
  private CajerosRecyclerViewAdapter.ViewHolder target;

  @UiThread
  public CajerosRecyclerViewAdapter$ViewHolder_ViewBinding(CajerosRecyclerViewAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.cardView = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'cardView'", CardView.class);
    target.nombre = Utils.findRequiredViewAsType(source, R.id.nombre1, "field 'nombre'", TextView.class);
    target.direccion = Utils.findRequiredViewAsType(source, R.id.direccion1, "field 'direccion'", TextView.class);
    target.foto = Utils.findRequiredViewAsType(source, R.id.foto1, "field 'foto'", ImageView.class);
    target.img_valoracion = Utils.findRequiredViewAsType(source, R.id.imag_valoracion, "field 'img_valoracion'", ImageView.class);
    target.valoracion = Utils.findRequiredViewAsType(source, R.id.valoracion1, "field 'valoracion'", RatingBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CajerosRecyclerViewAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cardView = null;
    target.nombre = null;
    target.direccion = null;
    target.foto = null;
    target.img_valoracion = null;
    target.valoracion = null;
  }
}
