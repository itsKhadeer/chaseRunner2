package com.example.chaserunner2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class characterSelect extends AppCompatActivity {



    RecyclerView charactersShow;
    Button backButton;
    MyApi api;
    ArrayList<recViewModel> arrCharacterDetail = new ArrayList<>();
    adapterForRecViewCharacterDetails adapter = new adapterForRecViewCharacterDetails(arrCharacterDetail,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            SplashScreenActivity.gameStart = true;
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-obstacle-dodge.vercel.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MyApi.class);


        charactersShow = findViewById(R.id.charactersRecView);
        getWindow().setStatusBarColor(getResources().getColor(R.color.skyBlue, this.getTheme()));


        listingData("player");
        listingData("chaser");
        charactersShow.setAdapter(adapter);
        charactersShow.setLayoutManager(new GridLayoutManager(this,2));
    }
    public void listingData(String type) {
        CharacterRequest request = new CharacterRequest(type);
        Call<CharacterResponse> call = api.createCharacter(request);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(@NonNull Call<CharacterResponse> call, @NonNull Response<CharacterResponse> response) {
                if (response.isSuccessful()) {
                    // Request successful, handle the response
                    CharacterResponse characterResponse = response.body();
                    Character character;

                        for(int i = 0; i < Objects.requireNonNull(characterResponse).getCharacters().size(); i++) {
                            character = characterResponse.getCharacters().get(i);
                            String charTypeAndDescription = "Type: " + character.getType() + "\n" +
                                    "Description: " + character.getDescription();
                            arrCharacterDetail.add(new recViewModel(character.getImageUrl(), character.getName(), charTypeAndDescription));
                            adapter.notifyItemChanged(i);
                        }

                    // Do something with the response data
                } else {
                    // Request failed, handle the error
                    // Access error information using response.errorBody()
                    arrCharacterDetail.add(new recViewModel("https://w7.pngwing.com/pngs/633/779/png-transparent-spider-man-super-hero-character-spiderverse-comic-marvel.png", String.valueOf(arrCharacterDetail.size()),"type: not successful \nDescription: He slings around the city like a spider and runs away from the chaser like very fast!!"));
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                // Request failed, handle the failure
                arrCharacterDetail.add(new recViewModel("https://w7.pngwing.com/pngs/633/779/png-transparent-spider-man-super-hero-character-spiderverse-comic-marvel.png", String.valueOf(arrCharacterDetail.size()),"type: failure \nDescription: He slings around the city like a spider and runs away from the chaser like very fast!!"));
            }
        });
    }
}