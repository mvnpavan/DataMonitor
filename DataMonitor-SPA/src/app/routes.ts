import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MemeberlistComponent } from './memeberlist/memeberlist.component';
import { MessagesComponent } from './messages/messages.component';
import { ListsComponent } from './lists/lists.component';
import { AuthGuard } from './_guards/auth.guard';

export const appRoutes: Routes = [
    { path: '', component: HomeComponent},
    // { path: 'members', component: MemeberlistComponent, canActivate: [AuthGuard]}, we add guard to single route if required
    {
        path: '',
        runGuardsAndResolvers: 'always',
        canActivate: [AuthGuard],
        children: [
            { path: 'members', component: MemeberlistComponent},
            { path: 'messages', component: MessagesComponent},
            { path: 'lists', component: ListsComponent}
        ]
    },
    { path: '**', redirectTo: '', pathMatch: 'full'}
];
