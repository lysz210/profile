<#ftl output_format="HTML">
<!DOCTYPE html>
<#assign me=.data_model['/me/index.yaml']>
<html lang="{{ App::currentLocale() }}">
<head>
    <meta charset="utf-8">

    <!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame
    Remove this if you use the .htaccess -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <title>Curriculum vitae di ${ me.name } ${ me.surname }</title>
    <meta name="description" content="Curriculum vitae di ${ me.name } ${ me.surname } aggiornato a Settembre 2015">
    <meta name="author" content="${ me.name } ${ me.surname }">
    <meta name="keywords" content="curriculum vitae, cv, curriculum, ${ me.name }, ${ me.surname }, ${ me.name } ${ me.surname }"/>

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">

    <!-- Replace favicon.ico & apple-touch-icon.png in the root of your domain and delete these references -->
    <link rel="stylesheet" type="text/css" href="{{ $path('/css/main_style.css') }}">
</head>

<body>
<pre>
    <#list .data_model?keys as k>
        ${k}
    </#list>
</pre>
    <header>
    	<h1>
    		<img class="logo-auropass" src="{{ $path('/images/europass-inline.svg') }}" alt="logo europass" title="logo europass" />
    		<span>Curriculum Vitae</span>
    	</h1>
    </header>
    <!--
    informazioni personali
    -->
    <section id="informazioni_personali">
        <h1>{{__('cv.anagrafica.title')}}</h1>
        <dl>
            <dt>
                {{__('cv.anagrafica.nominativo.title')}}
            </dt>
            <dd>
                <p>
                    <span id="nome">${ me.name }</span> <span id="cognome">${ me.surname }</span>
                </p>
            </dd>
            @if($withMail)
            <dt>Email</dt>
            <dd>{{ env('MAIL_PERSONAL') }}</dd>
            @endif
            <dt>
                {{__('cv.anagrafica.socials.title')}}
            </dt>
            <dd>
                <dl id="contatti_social">
                    @foreach (json_decode(Storage::disk('data')->get('me/social-accounts.json')) as $social)
                    <dt>{{ $social->name }}</dt>
                    <dd>
                        <a href="{{ $social->url }}" target="blank">{{ $social->username }}</a>
                    </dd>
                    @endforeach
                    <dt>
                        Skype
                    </dt>
                    <dd class="last">
                        lysz210
                    </dd>
                    <dt class="print_only">
                        Curriculum online
                    </dt>
                    <dd class="print_only">
                        <a href="{{Request::url()}}" target="blank">Curriculum vitae online</a>
                    </dd>
                </dl>
            </dd>
        </dl>
    </section>
    <!-- fine informazioni personali -->

    <!--
    esperienze lavorative
    -->
    <section id="esperienze_professionali">
        <h1>{{__('cv.lavoro.title')}}</h1>
        <dl>
        @foreach(__('cv.lavoro.esperienze') as $esperienza)
            <dt>{{$esperienza['periodo']}}</dt>
            <dd>
                <h2>{!! $esperienza['ruolo'] !!}</h2>
                <p>
                    {!! $esperienza['azienda'] !!}
                </p>
                @isset($esperienza['attivita'])
                <ul>
                @foreach($esperienza['attivita'] as $attivita)
                    <li>{{ $attivita }}</li>
                @endforeach
                </ul>
                @endisset
                <p class="settore">
                    {{ $esperienza['settore'] }}
                </p>
            </dd>
        @endforeach
    </section>
    <!-- fine esperienze lavorative -->

    <!-- istruzione e formazione -->
    <section id="istruzione e formazione">
        <h1>{{ __('cv.istruzione.title') }}</h1>
        <dl>
        @foreach(__('cv.istruzione.corsi') as $corso)
            <dt>
                {{ $corso['periodo'] }}
            </dt>
            <dd>
                {!! $corso['istituto'] !!}
                <p>
                    {!! $corso['corso'] !!}
                </p>
            </dd>
        @endforeach
        </dl>
    </section>
    <!-- fine iscruzione e formazione -->

    <!-- competenze -->
    <section id="competenze_personali">
        <h1>{{ __('cv.competenze.title') }}</h1>

        <dl>

            <dt>
                {{ __('cv.competenze.lingue.madre.title') }}
            </dt>
            <dd>
                {{ __('cv.competenze.lingue.madre.content') }}
            </dd>

            <dt>
                {{ __('cv.competenze.lingue.altre.title') }}
            </dt>
            <dd>
                <table>
                    <thead>
                        <tr>
                            <th> </th>
                            @foreach(__('cv.competenze.lingue.altre.cols') as $col)
                            <th>{{ $col }}</th>
                            @endforeach
                        </tr>
                    </thead>
                    <tbody>
                        @foreach(__('cv.competenze.lingue.altre.list') as $lingua)
                        <tr>
                            <td>{{ $lingua['name'] }}</td>
                            @foreach(__('cv.competenze.lingue.altre.cols') as $k => $v)
                            <td>{{ $lingua[$k] }}</td>
                            @endforeach
                        </tr>
                        @endforeach
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                {!! __('cv.competenze.lingue.altre.footer') !!}
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </dd>

            <dt>
                {{ __('cv.competenze.informatiche.title') }}
            </dt>
            <dd>
                <x-lista :value="__('cv.competenze.informatiche.list')"></x-lista>
            </dd>

            <dt>
                {{ __('cv.competenze.patente.title') }}
            </dt>
            <dd>
                {{ __('cv.competenze.patente.content') }}
            </dd>
        </dl>
    </section>
    <!-- fine competenze -->

    @if(Lang::has('cv.cdt'))
    <section id="consenso_td">
    	<h1>
    		{{ __('cv.cdt.title') }}
    	</h1>
    	<p>{{ __('cv.cdt.content') }}</p>
    </section>
    @endif
</body>
</html>
