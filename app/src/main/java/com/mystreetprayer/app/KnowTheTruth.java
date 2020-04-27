package com.mystreetprayer.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import java.util.Objects;

public class KnowTheTruth extends AppCompatActivity {

    private CardView bookKnowTruth, bookHaveYouTruely, bookEatMyFlesh, bookStrengthen, bookGodsRightious,
            bookRetturnToGospel,bookLordsPrayer, bookTheRelationship,bookTheSpiritualBlessings,bookTheWillOfHoly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_the_truth);

         bookKnowTruth = (CardView) findViewById(R.id.knowTruthBook);
         bookHaveYouTruely = (CardView) findViewById(R.id.have_you_truely);
         bookEatMyFlesh = (CardView) findViewById(R.id.eat_my_flesh);
         bookStrengthen = (CardView) findViewById(R.id.how_can_you_strengthen);
         bookGodsRightious = (CardView) findViewById(R.id.gods_righteous);
         bookRetturnToGospel = (CardView) findViewById(R.id.return_to_the_gospel);
         bookLordsPrayer = (CardView) findViewById(R.id.the_lords_prayer);
         bookTheRelationship = (CardView) findViewById(R.id.the_relationship);
         bookTheSpiritualBlessings = (CardView) findViewById(R.id.the_spiritual_blessings);
         bookTheWillOfHoly = (CardView) findViewById(R.id.the_will_of_holy);

        //Method Binding
        book_KnowTruth();
        book_HaveYouTruely();
        book_EatMyFlesh();
        book_Strengthen();
        book_GodsRightious();
        book_RetturnToGospel();
        book_LordsPrayer();
        book_TheRelationship();
        book_TheSpiritualBlessings();
        book_TheWillOfHoly();


        Toolbar toolbar = (Toolbar) findViewById(R.id.knowtheTruthToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

    }

    private void book_TheWillOfHoly() {

        bookTheWillOfHoly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=18a39s74sHMHEpn5BFzbI96L-zfhpuFhG";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_TheSpiritualBlessings() {

        bookTheSpiritualBlessings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=12YMA5t3BJGO59unw7GsNiJ8w9qJJN7aB";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_TheRelationship() {

        bookTheRelationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=1ddZvBcl6nyq4sy1p6RNMvYzyrqn8BgCL";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_LordsPrayer() {

        bookLordsPrayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=1Sy4iwIc1AqQ6h5OLDqX36nR2rCwpadOI";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_RetturnToGospel() {

        bookRetturnToGospel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=1QxPm079epxKWtwdyG99N6ERtZRm6YilT";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_GodsRightious() {

        bookGodsRightious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=1ozGASlR1uc3Dsi7iUScQbXtmWoxlTQoR";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_Strengthen() {

        bookStrengthen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=1cSws2vWauyj1PNEgqy7ra-P3H2mD7ZMo";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_EatMyFlesh() {

        bookEatMyFlesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=14NhKywgf70h5KP8tKZSzyEsxLvBKj8We";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_HaveYouTruely() {

        bookHaveYouTruely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=1sVzfKS8Q7DctcML0-qmLiBQuNFa7FhEy";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    private void book_KnowTruth() {

        bookKnowTruth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pdf_url = "https://drive.google.com/open?id=1DEj3ikEYbu2wsx2kEyO5V2rBAYaMtzbx";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_url));
                startActivity(browserIntent);
            }
        });

    }

    //Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
